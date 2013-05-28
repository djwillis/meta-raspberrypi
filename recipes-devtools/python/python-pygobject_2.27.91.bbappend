FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

do_install_append() {
  rm -f ${D}/usr/lib/libpyglib-2.0-python.la
}

FILES_${PN} += "${datadir}/* ${bindir}"
FILES_${PN}-dbg += "${libdir}/.debug ${PYTHON_DIR}/site-packages/gtk-2.0/gio/.debug \
  ${PYTHON_DIR}/site-packages/gobject/.debug ${PYTHON_DIR}/site-packages/glib/.debug"
#FILES_${PN}-dev = "${includedir} ${libdir}"
FILES_${PN}-doc += "${datadir}/gtk-doc/"
