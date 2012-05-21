require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "r0"

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

SRCREV = "4c9c54713c4b26c920f0fd73f245d415fe8e7ca0"
SRC_URI = "git://github.com/raspberrypi/linux.git;protocol=git"
S = "${WORKDIR}/git"
