FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC := "${@int(PRINC) + 2}"

SRC_URI += "file://bluetooth-agent.init"

RDEPENDS_${PN}-agent = "bluez4"

INITSCRIPT_NAME_${PN}-agent = "bluetooth-agent"
INITSCRIPT_PARAMS_${PN}-agent = "start 65 2 3 4 5 . stop 35 0 1 6 ."

do_install_append() {
  install -d ${D}${sysconfdir}/init.d
  install -m 0755 ${WORKDIR}/bluetooth-agent.init ${D}${sysconfdir}/init.d/bluetooth-agent

  install -m 0755 ${S}/lib/test-agent ${D}${bindir}/bluetooth-agent
}

PACKAGES =+ "gnome-bluetooth-agent"
FILES_${PN}-agent += "${bindir}/bluetooth-agent ${sysconfdir}/init.d/bluetooth-agent"

