# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Inherit sdcard creation bbclass
inherit sdcard-rpi

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
	"
