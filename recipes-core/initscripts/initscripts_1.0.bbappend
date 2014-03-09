FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://volatiles "

do_install_append() {
    install -d ${D}${sysconfdir}/default/volatiles
    cp -f ${WORKDIR}/volatiles ${D}${sysconfdir}/default/volatiles/00_core
}
