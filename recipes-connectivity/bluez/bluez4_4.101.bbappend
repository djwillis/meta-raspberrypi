FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC := "${@int(PRINC) + 5}"

SRC_URI_append = "file://bluez-plugdev.patch \
                  file://bluetooth-agent.init \
                  file://bluetooth-agent.conf \
                  file://bluetooth"

inherit pkgconfig update-rc.d

export PKG_CONFIG_PATH="${STAGING_LIBDIR}/pkgconfig"

EXTRA_OECONF = "\
  --disable-silent-rules \
  --enable-gstreamer \
  --enable-usb \
  --enable-alsa \
  --enable-tools \
  --enable-bccmd \
  --enable-hid2hci \
  --enable-dfutool \
  --enable-hidd \
  --enable-pand \
  --enable-dund \
  --disable-cups \
  --disable-service \
  --enable-health \
  --enable-test \
  --enable-datafiles \
"

INITSCRIPT_NAME = "bluetooth"
INITSCRIPT_PARAMS = "start 45 2 3 4 5 . stop 55 0 1 6 ."

INITSCRIPT_NAME_${PN}-agent = "bluetooth-agent"
INITSCRIPT_PARAMS_${PN}-agent = "start 65 2 3 4 5 . stop 35 0 1 6 ."

do_install_append() {
  rm ${D}/usr/lib/gstreamer-0.10/libgstbluetooth.la

  install -d ${D}${sysconfdir}/init.d
  install -d ${D}${sysconfdir}/default
  install -m 0755 ${WORKDIR}/bluetooth ${D}${sysconfdir}/init.d/
  install -m 0755 ${WORKDIR}/bluetooth-agent.init \
    ${D}${sysconfdir}/init.d/bluetooth-agent
  install -m 0644 ${WORKDIR}/bluetooth-agent.conf \
    ${D}${sysconfdir}/default/bluetooth-agent

  # add more bluetooth utilities
  TOOLS="list-devices simple-endpoint simple-player simple-agent \
  simple-service test-service test-network test-audio test-adapter \
  test-device test-discovery"

  for exe in $TOOLS; do
    install -m 0755 ${S}/test/$exe ${D}/${bindir}/bluez-$exe
  done

  install -m 0755 ${S}/test/monitor-bluetooth ${D}/${bindir}
  install -m 0755 ${S}/test/agent ${D}/${bindir}/bluetooth-agent
}

PACKAGES =+ "bluez4-agent"
RPROVIDES_${PN}-agent = "bluetooth-agent"

FILES_${PN} += "/usr/lib/gstreamer-0.10/*"
FILES_${PN}-agent = "${sysconfdir}/init.d/bluetooth-agent \
 ${bindir}/bluetooth-agent ${sysconfdir}/default/bluetooth-agent"
