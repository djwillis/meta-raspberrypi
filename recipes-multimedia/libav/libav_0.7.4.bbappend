FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC = "2"

SRC_URI_append = " file://libavcodec-ac3dsp_armv6-patch-out-armv7.patch"

CPPFLAGS += "-mfpu=vfp"
