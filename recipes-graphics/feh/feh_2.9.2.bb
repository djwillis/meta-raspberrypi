DESCRIPTION = "feh is a fast, lightweight image viewer which uses imlib2."
HOMEPAGE = "http://feh.finalrewind.org/"
SECTION = "x11/utils"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=364a0fbae4ab4bac979efa128780258a"

DEPENDS = "virtual/imlib2 giblib jpeg virtual/libx11 libxext libxt"
RDEPENDS_${PN} += "imlib2-filters imlib2-loaders"

SRC_URI = "http://feh.finalrewind.org/${P}.tar.bz2 \
"

S = "${WORKDIR}/feh-${PV}"

inherit autotools

do_install() {
    oe_runmake DESTDIR=${D} PREFIX=/usr install-bin
}

SRC_URI[md5sum] = "2a179cc12e0f7bf704b0dcf877d10e70"
SRC_URI[sha256sum] = "24d0be004b16207981aa37a24da9eb72718978ffec28be1eeea64b9541d66d3e"
