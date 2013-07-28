FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

do_configure_prepend() {
    # fix dbus init script path
    sed -i -e "s|/var/run/messagebus.pid|/var/run/dbus/pid|" \
        ${WORKDIR}/dbus-1.init
}
