FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 2}"

SRC_URI_append = "file://bluez-plugdev.patch \
                  file://bluetooth"

inherit pkgconfig update-rc.d

export PKG_CONFIG_PATH="${STAGING_LIBDIR}/pkgconfig"

EXTRA_OECONF = "\
  --disable-silent-rules \
  --enable-gstreamer \
  --enable-usb \
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

do_install_append() {
  rm ${D}/usr/lib/gstreamer-0.10/libgstbluetooth.la

  install -d ${D}${sysconfdir}/init.d
  install -m 0755 ${WORKDIR}/bluetooth ${D}${sysconfdir}/init.d/  
}

pkg_postinst_bluez4() {
if test "x$D" != "x"; then
    exit 1
else
    /etc/init.d/bluetooth stop
    sleep 1
fi
}

FILES_${PN} += "/usr/lib/gstreamer-0.10/*"
