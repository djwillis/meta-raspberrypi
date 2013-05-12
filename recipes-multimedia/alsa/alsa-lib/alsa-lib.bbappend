FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

do_configure_prepend() {
    sed -i -e "s|pcm.front cards.pcm.front|pcm.front cards.pcm.default|" \
        ${S}/src/conf/alsa.conf
}
