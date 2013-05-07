FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 5}"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI_append = " file://mini_x.session "

do_install_append () {
	install -d ${D}/${sysconfdir}/mini_x
	install -m 0755 ${WORKDIR}//mini_x.session ${D}/${sysconfdir}/mini_x/session
}

FILES_${PN}-config += "${sysconfdir}/mini_x/*"
