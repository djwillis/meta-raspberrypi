DESCRIPTION = "A custom console image based on rpi and core images"
# see meta-alt-desktop-extras for a much more full-featured console image

PR = "r0"

include rpi-basic-image.bb

EXTRA_IMAGE_FEATURES = "debug-tweaks"

IMAGE_FEATURES += " \
    package-management \
    hwcodecs \
    ${EXTRA_IMAGE_FEATURES} \
"

IMAGE_INSTALL += " \
    rpiuser-account \
    sudo \
    nano \
"

export IMAGE_BASENAME = "rpi-console-image"
