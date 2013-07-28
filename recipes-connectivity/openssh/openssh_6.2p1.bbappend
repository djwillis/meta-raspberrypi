FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

SRC_URI += "file://sshd.busybox-init"
do_confgure_append() {
    install -d ${D}${sysconfdir}/init.d
    cp -f ${WORKDIR}/sshd.busybox-init ${WORKDIR}/init
}

