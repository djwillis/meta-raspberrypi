require linux.inc

DESCRIPTION = "Linux kernel for the RaspberryPi board"
COMPATIBLE_MACHINE = "raspberrypi"

PR = "r6"
PV_append = "+git${SRCREV}"

SRCREV = "31a951046155b27361127d9cf85a1f58719fe9b3"
SRC_URI = "git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-3.6.y \
          "
S = "${WORKDIR}/git"

# NOTE: For now we pull in the default config from the RPi kernel GIT tree.
KERNEL_DEFCONFIG = "bcmrpi_defconfig"

# CMDLINE for raspberrypi
CMDLINE_raspberrypi = "dwc_otg.lpm_enable=0 console=ttyAMA0,115200 kgdboc=ttyAMA0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rootwait"

UDEV_GE_141 ?= "1"

do_configure_prepend() {
	install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}

do_install_prepend() {
	install -d ${D}/lib/firmware
}

do_deploy_append() {
	# Deploy cmdline.txt
	install -d ${DEPLOYDIR}/bcm2835-bootfiles
	echo "${CMDLINE}" > ${DEPLOYDIR}/bcm2835-bootfiles/cmdline.txt
}
