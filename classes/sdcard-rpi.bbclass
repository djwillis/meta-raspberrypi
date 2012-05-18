inherit image_types

IMAGE_BOOTLOADER ?= "bcm2835-bootfiles"
IMAGE_KERNEL     ?= "bcm2835-kernel-image"

#
# Create an image that can by written onto a SD card using dd.
#
# External variables needed:
#   ${SDCARD_ROOTFS}    - the rootfs image to incorporate
#
# The disk layout used is:
#
#    0  - 1M                  - reserved for other data
#    1M - BOOT_SPACE          - bootloader and kernel
#    BOOT_SPACE - SDCARD_SIZE - rootfs
#

# Default to 1.4GiB images
SDCARD_SIZE ?= "1400"

# Boot partition volume id
BOOTDD_VOLUME_ID ?= "Boot-${MACHINE}"

# Addional space for boot partition
BOOT_SPACE ?= "10MiB"

# Use an ext3 by default as rootfs
SDCARD_ROOTFS ?= "${IMAGE_NAME}.rootfs.ext3"

IMAGE_DEPENDS_rpi-sdimg = "parted-native \
                           mtools-native \
                           dosfstools-native \
                           ${IMAGE_KERNEL} \
                           ${IMAGE_BOOTLOADER}"

SDCARD = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.rpi-sdimg"

IMAGE_CMD_rpi-sdimg () {
    # Initialize sdcard image file
    dd if=/dev/zero of=${SDCARD} bs=1 count=0 seek=$(expr 1000 \* 1000 \* ${SDCARD_SIZE})
    # Create partition table
    parted -s ${SDCARD} mklabel msdos
    parted -s ${SDCARD} mkpart primary 1MiB ${BOOT_SPACE}
    parted -s ${SDCARD} mkpart primary ${BOOT_SPACE} 100%
    parted ${SDCARD} print

    BOOT_BLOCKS=$(LC_ALL=C parted -s ${SDCARD} unit b print | awk '/ 1 / { print substr($4, 1, length($4 -1)) / 512 /2 }')
    mkfs.vfat -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${WORKDIR}/boot.img $BOOT_BLOCKS
    mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/* ::

    dd if=${WORKDIR}/boot.img of=${SDCARD} conv=notrunc seek=1 bs=1M
    dd if=${SDCARD_ROOTFS} of=${SDCARD} conv=notrunc seek=1 bs=${BOOT_SPACE}
}
