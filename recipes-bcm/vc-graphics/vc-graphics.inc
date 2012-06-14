DESCRIPTION = "Graphics libraries for BCM2835."
LICENSE = "proprietary-binary"

LIC_FILES_CHKSUM = "file://LICENCE;md5=86e53f5f5909ee66900418028de11780"

PROVIDES += "virtual/egl"

SRCREV = "1178c4db57e8676f331fdacfb37ff1662430ea5f"

SRC_URI = "git://github.com/raspberrypi/firmware.git;protocol=git;branch=master"

S = "${WORKDIR}/git/${VCDIR}"

INCPR = "r0"

do_install () {
  install -d ${D}${bindir}
  cp -R bin/* ${D}${bindir}

  install -d ${D}${sbindir}
  cp -R sbin/* ${D}${sbindir}

  install -d ${D}${libdir}
  cp -R lib/* ${D}${libdir}

  install -d ${D}${includedir}
  cp -R include/* ${D}${includedir}
}

FILES_${PN} += "${libdir}"

