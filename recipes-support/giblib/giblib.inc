DESCRIPTION = "giblib is a utility library that incorporates doubly linked \
lists, some string functions, and a wrapper for imlib2."
SECTION = "libs"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=dd3cb8d7a69f3d0b2a52a46c92389011"

DEPENDS = "virtual/imlib2"

SRC_URI = "http://linuxbrit.co.uk/downloads/giblib-${PV}.tar.gz \
           file://binconfig.patch"

S = "${WORKDIR}/giblib-${PV}"

inherit autotools pkgconfig binconfig

export PKG_CONFIG_PATH="${STAGING_LIBDIR}/pkgconfig"

do_configure_prepend() {
    autopoint || touch config.rpath
    autoreconf -Wcross --verbose --install --force ${EXTRA_AUTORECONF} $acpaths \
        || bbnote "failed to autoreconf"
}

do_install() {
    make DESTDIR=${D} PREFIX=/usr install
    install -d ${D}/usr/share/doc
    mv ${D}/usr/doc/* ${D}/usr/share/doc/
    rm -rf ${D}/usr/doc
}

FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev += "${bindir}/giblib-config"
