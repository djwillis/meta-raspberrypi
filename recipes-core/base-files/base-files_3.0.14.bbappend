FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

do_install_append() {
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/
}
