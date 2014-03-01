DESCRIPTION = "A custom console image based on rpi and core images"
PR = "r1"

include rpi-hwup-image.bb

DISTRO_FEATURES = "alsa argp bluetooth ext2 irda largefile nfc pam pci \
    sysvinit usbgadget usbhost wifi xattr nfs zeroconf \
    ${DISTRO_FEATURES_LIBC}"

EXTRA_IMAGE_FEATURES = "debug-tweaks"

IMAGE_FEATURES += " ssh-server-openssh splash package-management hwcodecs \
    ${EXTRA_IMAGE_FEATURES} "

# set the following parameters here (defaults in local.conf)
DEFAULT_TIMEZONE = "PST8PDT"

# possible values are internal or external
CUSTOM_FEED_CONFIG = "external"

IMAGE_INSTALL += " \
  ${CORE_IMAGE_BASE_INSTALL} \
  ${CORE_IMAGE_EXTRA_INSTALL} \
"

include console-extras.inc

export IMAGE_BASENAME = "rpi-console-image"
