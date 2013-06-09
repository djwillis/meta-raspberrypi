DESCRIPTION = "A custom image based on core-image and x11-sato."

# don't include images based on core-image-minimal

IMAGE_FEATURES += "package-management ssh-server-dropbear splash \
    x11-base x11-sato hwcodecs"

# core-image bbclass provides core-boot and base packages
inherit core-image

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    packagegroup-base-alsa \
    packagegroup-core-x11 \
    nano \
    zram \
    gkrellm-daemon \
    cpufrequtils \
    connman-tests \
"

