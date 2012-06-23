# Ensure required utilities are present
IMAGE_DEPENDS_rpi-sdimg = "genext2fs-native dosfstools-native e2fsprogs-native bcm2835-bootfiles bcm2835-kernel-image"

# Register this as an available type of image.
IMAGE_TYPES_append = " rpi-sdimg"

# Default to 4GiB images
SDIMG_SIZE ?= "444" 

# FS type for rootfs
ROOTFSTYPE ?= "ext4"

BOOTPARTNAME ?= "${MACHINE}"

IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

# Additional files and/or directories to be copied into the vfat partition from the IMAGE_ROOTFS.
FATPAYLOAD ?= ""

IMAGE_CMD_rpi-sdimg () {
	SDIMG=${WORKDIR}/sd.img

	# If an SD image is already present, reuse and reformat it
	if [ ! -e ${SDIMG} ] ; then
		dd if=/dev/zero of=${SDIMG} bs=$(echo '255 * 63 * 512' | bc) count=${SDIMG_SIZE}
	fi

	# Create partition table
	dd if=/dev/zero of=${SDIMG} bs=1024 count=1024 conv=notrunc
	SIZE=$(ls -l ${SDIMG} | awk '{print $5}')
	CYLINDERS=$(echo $SIZE/255/63/512 | bc)
	{
	echo ,9,0x0C,*
	echo ,,,-
	} | /sbin/sfdisk -D -H 255 -S 63 -C ${CYLINDERS} ${SDIMG}

	# Get partition offsets and sizes
	BOOT_OFFSET_SECT=63
	FS_OFFSET_SECT=$(/sbin/fdisk -l -u ${SDIMG} 2>&1 | grep Linux | perl -p -i -e "s/\s+/ /"|cut -d " " -f 2)
	FS_OFFSET=$(echo "$FS_OFFSET_SECT * 512" | bc)
	FS_SIZE_BLOCKS=$(/sbin/fdisk -l -u ${SDIMG} 2>&1 | grep Linux | perl -p -i -e "s/\s+/ /g" |cut -d " " -f 4 | cut -d "+" -f 1)
	BOOT_SIZE_BLOCKS=$(/sbin/fdisk -l -u ${SDIMG} 2>&1 | grep FAT | perl -p -i -e "s/\s+/ /g"|cut -d " " -f 5)

	echo "Creating boot filesystem"
	FAT_BLOCKS=$(echo "$BOOT_SIZE_BLOCKS / 16 * 16" | bc)
	dd if=/dev/zero of=${SDIMG}.vfat bs=1024 count=$FAT_BLOCKS
	/sbin/mkfs.vfat -F 32 ${SDIMG}.vfat -n ${BOOTPARTNAME} $FAT_BLOCKS

	# Prepare boot partition

	echo "Copying bootloader and prepended kernel.img into the boot partition"
	mcopy -i ${SDIMG}.vfat -v ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/* ::

	if [ -n ${FATPAYLOAD} ] ; then
		echo "Copying payload into VFAT"
		for entry in ${FATPAYLOAD} ; do
				# add the || true to stop aborting on vfat issues like not supporting .~lock files
				mcopy -i ${SDIMG}.vfat -s -v ${IMAGE_ROOTFS}$entry :: || true
		done
	fi

	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${IMAGE_ROOTFS}/etc/image-version-info
	
	mcopy -i ${SDIMG}.vfat -v ${IMAGE_ROOTFS}/etc/image-version-info ::

	echo "Copying boot file system into image"
	dd if=${SDIMG}.vfat of=${SDIMG} ibs=1024 obs=512 count=$FAT_BLOCKS seek=$BOOT_OFFSET_SECT conv=notrunc
	rm ${SDIMG}.vfat

	# Prepare rootfs partition
	echo "Creating root filesystem of type ${ROOTFSTYPE}"

	FS_NUM_INODES=$(echo $FS_SIZE_BLOCKS / 4 | bc)

	TMP_ROOTFS=${SDIMG}.rootfs
	dd if=/dev/zero of=$TMP_ROOTFS bs=1024 count=$FS_SIZE_BLOCKS
	case "${ROOTFSTYPE}" in
		ext3)
				genext2fs -z -N $FS_NUM_INODES -b $FS_SIZE_BLOCKS -d ${IMAGE_ROOTFS} $TMP_ROOTFS
				tune2fs -L ${IMAGE_NAME} -j $TMP_ROOTFS
				;;
		ext4)
				genext2fs -z -N $FS_NUM_INODES -b $FS_SIZE_BLOCKS -d ${IMAGE_ROOTFS} $TMP_ROOTFS
				tune2fs -L ${IMAGE_NAME} -j -O extents,uninit_bg,dir_index $TMP_ROOTFS
				;;
		*)
				echo "Please set ROOTFSTYPE to something supported"
				exit 1
				;;
	esac

	echo "Copying root file system into image"
	dd if=$TMP_ROOTFS of=${SDIMG} ibs=1024 obs=512 count=$FS_SIZE_BLOCKS seek=$FS_OFFSET_SECT conv=notrunc
	rm $TMP_ROOTFS

	gzip -c ${WORKDIR}/sd.img > ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-${IMAGEDATESTAMP}.img.gz
	rm -f ${WORKDIR}/sd.img
}
