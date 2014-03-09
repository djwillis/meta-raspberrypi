DESCRIPTION = "A custom console image based on rpi and core images"
# see meta-alt-desktop-extras for a much more full-featured console image

PR = "r0"

include rpi-hwup-image.bb

EXTRA_IMAGE_FEATURES = "debug-tweaks"

IMAGE_FEATURES += " ssh-server-openssh splash package-management hwcodecs \
    ${EXTRA_IMAGE_FEATURES} "

IMAGE_INSTALL += " \
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    rpiuser-account \
    sudo \
"

# see meta-alt-desktop-extras for a much more full-featured console image

export IMAGE_BASENAME = "rpi-console-image"
