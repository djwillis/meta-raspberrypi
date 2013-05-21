DESCRIPTION = "A custom image based on core-image and x11."

# don't include images based on core-image-minimal

IMAGE_FEATURES += "package-management ssh-server-dropbear splash \
    x11-base hwcodecs"

# you can override the following three parameters in your local.conf
DEFAULT_TIMEZONE ?= "Universal"

# possible values are 1 (DVI only) or 2 (hdmi with audio)
DEFAULT_AUDIO_OUT ?= "2"

# possible values are internal or external
CUSTOM_FEED_CONFIG ?= "external"

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
    obconf \
    tint2 \
    xmodmap \
    xrdb \
    setxkbmap \
    userland \
    bash \
    nano \
    zram \
    gkrellm \
    gkrellm-client \
    cpufrequtils \
    connman-tests \
    gst-meta-video \
    gst-meta-audio \
    alsa-utils-speakertest \
    mpd \
    mc \
    vim \
    file \
    ntp \
    tzdata \
    ntpdate \
    usbutils \
    sysfsutils \
    imagemagick \
    xchat \
    vala-terminal \
    leafpad \
    midori \
    pcmanfm \
    xfmpc \
"

# core-image bbclass provides core-boot and base packages
inherit core-image
