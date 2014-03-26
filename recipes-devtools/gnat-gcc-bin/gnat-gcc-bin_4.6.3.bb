DESCRIPTION = "GNAT GPL compiler built from Gentoo gcc sources."
LICENSE="GPL-3.0-with-GCC-exception & GPLv3"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

PROVIDES_${PN} += "gnat-gcc"

COMPATIBLE_MACHINE = "raspberrypi"

BIN_URL = "www.gentoogeek.org"
BIN_PKG = "gnat-gcc-4.6.3-armv6j-hardfloat-linux-gnueabi-native-shared-bin.tar.bz2"

SRC_URI = "http://${BIN_URL}/files/arm-gnat-gcc/${BIN_PKG};unpack=false \
           file://COPYING \
"

S = "${WORKDIR}"

INCPR = "r0"

do_install () {
    tar xjf ${WORKDIR}/${BIN_PKG} -C ${D}
}

PACKAGES += "gnat-gcc-runtime-dbg gnat-gcc-runtime-staticdev gnat-gcc-runtime"

FILES_gnat-gcc-runtime-dbg += "${libdir}/gcc/*/*/.debug ${libdir}/gcc/*/*/*/.debug"
FILES_gnat-gcc-runtime-staticdev += "${libdir}/gcc/*/*/*.a ${libdir}/gcc/*/*/*/*.a"
FILES_gnat-gcc-runtime = "${libdir}/gcc"

RDEPENDS_${PN} += "gnat-gcc-runtime gnat-gcc-runtime-staticdev"

# These are binaries generated elsewhere so don't check ldflags
INSANE_SKIP = "true"

SRC_URI[md5sum] = "78c9db1ec1d2226e145810a3bbe7f396"
SRC_URI[sha256sum] = "85d09d08fae5f5266ca50f686a08d34480fa6fead1052f1a6e93cd18a7cfcd8d"

