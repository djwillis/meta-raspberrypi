DESCRIPTION = "Open-source JPEG 2000 codec written in C language"
HOMEPAGE = "http://www.openjpeg.org"
SECTION = "libs"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1153c321aaa5ae2733736b49673563d9"

PR = "r0"
BBCLASSEXTEND = "native"

SRC_URI = "http://openjpeg.googlecode.com/files/openjpeg-${PV}.tar.gz"

S = "${WORKDIR}/openjpeg-${PV}"

inherit cmake

EXTRA_OECMAKE="-DBUILD_SHARED_LIBS:BOOL=ON"

do_install_append() {
    rm -rf ${D}/usr/lib/openjpeg-1.5
}

do_install_append_class-native() {
    sed -i -e "s|prefix=/usr|prefix=${STAGING_DIR_NATIVE}/usr|" \
        ${S}/libopenjpeg1.pc

    install -d ${D}/${libdir}/pkgconfig
    install ${S}/libopenjpeg1.pc ${D}/${libdir}/pkgconfig/libopenjpeg.pc
}

PACKAGES =+ "openjpeg-tools "
FILES_openjpeg-tools = "${bindir}/*"

SRC_URI[md5sum] = "f9a3ccfa91ac34b589e9bf7577ce8ff9"
SRC_URI[sha256sum] = "3bca2e1e040f9dcbbcb1e0627f17a76eeb95e153bf663d082070c044a21202bd"
