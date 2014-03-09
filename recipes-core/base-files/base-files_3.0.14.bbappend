FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://modules"

conffiles += " ${sysconfdir}/modules"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/
}
