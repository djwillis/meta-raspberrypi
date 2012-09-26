COMPATIBLE_MACHINE = "raspberrypi"

require linux.inc

DESCRIPTION = "Linux kernel for the RaspberryPi board"

PR = "r4"

# Bump MACHINE_KERNEL_PR in the machine config if you update the kernel.
# This is on the rpi-3.2.27 branch
SRCREV = "e33263e7eab2d05349b438f6a4a8d1924686a824"

SRC_URI = "git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-3.2.27 \
          "

LINUX_VERSION ?= "3.2.27-rpi"
PV = "${LINUX_VERSION}+git${SRCREV}"

S = "${WORKDIR}/git"

# NOTE: For now we pull in the default config from the RPi kernel GIT tree.

KERNEL_DEFCONFIG = "bcmrpi_defconfig"

# CMDLINE for raspberrypi
CMDLINE_raspberrypi = "dwc_otg.lpm_enable=0 console=ttyAMA0,115200 kgdboc=ttyAMA0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rootwait"

PARALLEL_MAKEINST = ""

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
