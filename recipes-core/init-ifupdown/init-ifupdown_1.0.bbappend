FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 2}"

SRC_URI += "file://init"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    cp -f ${WORKDIR}/init ${D}${sysconfdir}/init.d/networking
}
