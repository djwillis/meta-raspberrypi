FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 2}"

inherit useradd

EXTRA_OECONF += "--enable-linuxcaps"

do_configure_prepend() {
    sed -i -e 's|NTPSERVERS=""|NTPSERVERS="pool.ntp.org"|' \
        ${WORKDIR}/ntpdate.default

#    sed -i -e "s|startdaemon -g|startdaemon -g -u ntp:ntp|g" \
#        ${WORKDIR}/ntpd

    sed -i \
        -e "s|etc/ntp.drift|var/lib/ntp/ntp.drift|" \
        -e "s|time.server.example.com|pool.ntp.org|" \
        -e "s|server 127|#server 127|" \
        -e "s|fudge 127|#fudge 127|" \
        ${WORKDIR}/ntp.conf
}

do_install_append() {
    rm -rf ${D}/lib/systemd
    install -d ${D}/${localstatedir}/lib/ntp
    chown ntp:ntp ${D}/${localstatedir}/lib/ntp
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
    --system --no-create-home \
    --home ${localstatedir}/lib/ntp \
    --groups uucp \
    --user-group ntp"

FILES_${PN} += "${localstatedir}/lib/ntp"
