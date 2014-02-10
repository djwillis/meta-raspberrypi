DESCRIPTION = "Closed source binary files to help boot the ARM on the BCM2835."
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://LICENCE.broadcom;md5=e86e693d19572ee64cc8b17fb062faa9"

inherit deploy

include ../common/firmware.inc

RDEPENDS_${PN} = "rpi-config"

COMPATIBLE_MACHINE = "raspberrypi"

S = "${RPIFW_S}/boot"

PR = "r3"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles
	for i in *.elf ; do
		cp $i ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles
	done
	for i in *.dat ; do
		cp $i ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles
	done
	for i in *.bin ; do
		cp $i ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles
	done

	# set a specific audio output for alsa (auto is known to be problematic)
	sed -i -e "s|#hdmi_drive=2|hdmi_drive=${DEFAULT_AUDIO_OUT}|" \
		${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/config.txt

	# Add stamp in deploy directory
	touch ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/${PN}-${PV}.stamp
}

addtask deploy before do_package after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

