DESCRIPTION = "A custom image based on core-image and x11."

# don't include images based on core-image-minimal

IMAGE_FEATURES += "package-management ssh-server-dropbear splash \
    x11-base hwcodecs"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    packagegroup-base-alsa \
    packagegroup-core-x11 \
    packagegroup-core-x11-base \
    xkbcomp \
    openbox \
    openbox-config \
    openbox-core \
    openbox-lxde \
    openbox-theme-bear2 \
    openbox-theme-clearlooks \
    openbox-theme-artwiz-boxed \
    xmodmap \
    xrdb \
    setxkbmap \
    bash \
    nano \
    zram \
    gkrellm-daemon \
    cpufrequtils \
    connman-tests \
    gst-meta-video \
    gst-meta-audio \
    mc \
    vim \
    file \
    usbutils \
    sysfsutils \
    imagemagick \
    xchat \
    vala-terminal \
    leafpad \
    midori \
    pcmanfm \
"

# core-image bbclass provides core-boot and base packages
inherit core-image
