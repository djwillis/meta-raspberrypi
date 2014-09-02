FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_raspberrypi = " file://alsa.conf "


require ${PN}.inc
