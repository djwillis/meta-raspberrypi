DESCRIPTION = "A module to control Raspberry Pi GPIO channels"
HOMEPAGE = "http://code.google.com/p/raspberry-gpio-python/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE.txt;md5=35af90ff2a10e8bdc967653b9dfcb22a"

SRCNAME = "RPi.GPIO"
PR = "r0"

SRC_URI = "\
          http://pypi.python.org/packages/source/R/RPi.GPIO/${SRCNAME}-${PV}.tar.gz \
          "
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

COMPATIBLE_MACHINE = "raspberrypi"

SRC_URI[md5sum] = "f7dc0330158b71ccb7837d3aca644362"
SRC_URI[sha256sum] = "62b9e482135af4d1b15624c824dcba15199d9c9d34d8b3e6e7ab5e6c517c9005"
