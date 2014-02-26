FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 3}"

SRC_URI_append = "file://alsa.conf"

require ${PN}.inc
