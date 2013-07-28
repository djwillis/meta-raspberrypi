SECTION = "x11/network"
DESCRIPTION="WiFi Radar is a Python/PyGTK2  utility for managing WiFi profiles."
HOMEPAGE="http://www.bitbuilder.com/wifi_radar/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r4"

PACKAGE_ARCH = "all"

RDEPENDS_${PN} = "dhcpcd \
    python-subprocess \
    python-core python-pygtk \
    python-re \
    python-io \
    python-pygobject \
    python-pycairo"

SRC_URI="${SOURCEFORGE_MIRROR}/${PN}.berlios/${PN}-${PV}.tar.bz2 \
         file://${P}-dhcpcd-args.patch \
"

S = "${WORKDIR}/${PN}-${PV}"

do_install() {
    oe_runmake sbindir=${D}${sbindir} \
        initdir=${D}${sysconfdir}/init.d \
        sysconfdir=${D}${sysconfdir} \
        mandir=${D}${mandir} \
        pixmapsdir=${D}${datadir}/pixmaps \
        appsdir=${D}${datadir}/applications install
}

SRC_URI[md5sum] = "cbad24805d5dc6696e38dc1df619c819"
SRC_URI[sha256sum] = "37479325556f1ce6d9309cb7906dcbd1b4e53e6e80616b6617556de7a6512f32"
