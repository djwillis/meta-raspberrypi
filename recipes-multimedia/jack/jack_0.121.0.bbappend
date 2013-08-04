FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI += "file://limits.conf"

inherit useradd

do_install_append() {
    install -d ${D}/${sysconfdir}/security
    install ${WORKDIR}/limits.conf ${D}/${sysconfdir}/security
}

USERADD_PACKAGES = "jack-server"
GROUPADD_PARAM_jack-server = "-g 888 realtime"

FILES_jack-server += "${sysconfdir}/security"
