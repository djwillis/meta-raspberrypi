FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

SRC_URI_append = "file://mpd"

inherit update-rc.d

INITSCRIPT_NAME = "mpd"
INITSCRIPT_PARAMS = "defaults 75"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0644 ${WORKDIR}/mpd ${D}${sysconfdir}/init.d/
}

FILES_${PN} += "${sysconfdir}/init.d/*"
