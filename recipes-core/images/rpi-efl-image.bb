DESCRIPTION = "A custom image based on core-image and x11/efl."

# don't include images based on core-image-minimal
# core-image bbclass provides core-boot and base packages
inherit core-image

export IMAGE_BASENAME = "rpi-efl-image"

IMAGE_FEATURES += "package-management ssh-server-dropbear splash \
    x11-base hwcodecs"

# you can override the following three parameters in your local.conf
DEFAULT_TIMEZONE ?= "Universal"

# possible values are 1 (DVI only) or 2 (hdmi with audio)
DEFAULT_AUDIO_OUT ?= "2"

# possible values are internal or external
CUSTOM_FEED_CONFIG ?= "external"

PREFERRED_PROVIDER_opkg-collateral = "opkg-collateral"
PREFERRED_PROVIDER_psplash-support = "psplash"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    ${EXTRA_TIMEZONES} \
    ${XSERVER} \
    ${WINDOW_MANAGER} \
    ${DESKTOP_APPS} \
    userland \
    bash \
    nano \
    zram \
    gkrellm \
    cpufrequtils \
    connman-tests \
    packagegroup-base-alsa \
    alsa-utils-speakertest \
    mpd \
    empcd \
    mc \
    vim \
    file \
    tzdata \
    ntp \
    ntpdate \
    ntp-utils \
    perl-module-socket \
    perl-module-getopt-std \
    usbutils \
    sysfsutils \
    bluez-hcidump \
    bluez4-agent \
"

XSERVER = " \
    packagegroup-core-x11 \
    packagegroup-core-x11-base \
    xserver-nodm-init-systemd \
    xkbcomp \
    xmodmap \
    xrdb \
    setxkbmap \
"    

WINDOW_MANAGER = " \
    e-wm-config-default \
    e-wm-config-standard \
    e-wm-config-illume2 \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    ttf-dejavu-common \
"

DESKTOP_APPS = " \
    imagemagick \
    xchat \
    vala-terminal \
    leafpad \
    leafpad-stock-icons \
    midori \
    pcmanfm \
    xfmpc \
    gkrellm-client \
    gst-meta-video \
    gst-meta-audio \
"

EXTRA_TIMEZONES = " \
    tzdata-africa \
    tzdata-americas \
    tzdata-antarctica \
    tzdata-arctic \
    tzdata-asia \
    tzdata-atlantic \
    tzdata-australia \
    tzdata-europe \
    tzdata-misc \
    tzdata-pacific \
    tzdata-posix \
    tzdata-right \
"
