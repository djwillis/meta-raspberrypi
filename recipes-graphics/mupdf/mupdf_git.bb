DESCRIPTION = "A lightweight PDF viewer and toolkit written in portable C."
HOMEPAGE = "http://www.mupdf.com"
SECTION = "x11/applications"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=73f1eb20517c55bf9493b7dd6e480788"

SRCREV = "1.2"
PV = "${SRCREV}"
PR = "r0"

DEPENDS = "openjpeg-native jbig2dec-native libjpeg-turbo-native freetype-native libpng-native openjpeg jbig2dec libjpeg-turbo zlib virtual/libx11 libxext freetype"

SRC_URI = "git://git.ghostscript.com/mupdf.git;protocol=http"

inherit cmake

S = "${WORKDIR}/git"

# mupdf crashes when built with -ggdb3
# http://bugs.ghostscript.com/show_bug.cgi?id=691578
FULL_OPTIMIZATION := "${@oe_filter_out('-ggdb3', '${FULL_OPTIMIZATION}', d)}"

do_configure() {
    # we don't include CJK fonts to make binary more slim
    # comment out following two lines if you need support for CJK
    sed -i 's:^\t\$.GENDIR./font_cjk.c::g' ${S}/Makefile
    echo "CFLAGS += -DNOCJK" >> ${S}/Makerules
}

do_compile() {
    # mupdf uses couple of tools for code generation during build process
    # so we need to compile them first with host compiler
    unset CFLAGS LDFLAGS
    export PKG_CONFIG_PATH=${STAGING_LIBDIR_NATIVE}/pkgconfig
    oe_runmake build=release build/release
    oe_runmake build=release build/release/cmapdump LD=${BUILD_CC} \
        LDFLAGS="-L${STAGING_LIBDIR_NATIVE} -Wl,-rpath,${STAGING_LIBDIR_NATIVE}" \
        CC=${BUILD_CC}
    oe_runmake build=release build/release/fontdump LD=${BUILD_CC} \
        LDFLAGS="-L${STAGING_LIBDIR_NATIVE} -Wl,-rpath,${STAGING_LIBDIR_NATIVE}" \
        CC=${BUILD_CC}

    export PKG_CONFIG_PATH=${STAGING_LIBDIR}/pkgconfig
    sed -i -e "s|cflags libopenjpeg|cflags libopenjpeg1|" ${S}/Makerules
    # ...and then we fire 'make', feeding proper
    # cross-compilation flags through Makerules file
    echo "CFLAGS += ${CFLAGS}" >> ${S}/Makerules
    echo "LDFLAGS += ${LDFLAGS}" >> ${S}/Makerules
    oe_runmake build=release LD="${CC}" XCFLAGS="-I${STAGING_INCDIR}" \
        XLIBS="-L${STAGING_LIBDIR} -Wl,-rpath-link,${STAGING_LIBDIR}" 
}

do_install() {
    oe_runmake DESTDIR="${D}" install build=release
    install -d ${D}/${datadir}/applications
    install -d ${D}/${datadir}/pixmaps
    install -d ${D}/${mandir}/man1
    install -m 0644 ${S}/debian/mupdf.xpm ${D}/${datadir}/pixmaps/
    install -m 0644 ${S}/debian/mupdf.desktop ${D}/${datadir}/applications/
}

PACKAGES =+ "${PN}-tools ${PN}-tools-doc "

FILES_${PN}-tools = "${bindir}/pdfclean ${bindir}/pdfdraw ${bindir}/pdfshow \
                     ${bindir}/pdfextract ${bindir}/pdfinfo"
FILES_${PN}-tools-doc = "${mandir}/man1/pdfclean.1 ${mandir}/man1/pdfdraw.1 \
                         ${mandir}/man1/pdfshow.1"
##FILES_${PN}-dev = "${includedir}"

