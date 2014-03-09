FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "file://alsa.conf"

require ${PN}.inc
