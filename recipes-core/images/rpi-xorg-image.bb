DESCRIPTION = "A custom image based on core-image and x11."

# don't include images based on core-image-minimal

IMAGE_FEATURES += "package-management ssh-server-openssh splash \
    x11-base hwcodecs"

# you can override the following three parameters in your local.conf
DEFAULT_TIMEZONE ?= "Universal"

# possible values are 1 (DVI only) or 2 (hdmi with audio)
DEFAULT_AUDIO_OUT ?= "2"

# possible values are internal or external
CUSTOM_FEED_CONFIG ?= "external"

# didn't work, so I have to make my own implementation later...
#ROOTLESS_X = "1"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    ${EXTRA_TIMEZONES} \
    ${XSERVER} \
    ${WINDOW_MANAGER} \
    ${DESKTOP_APPS} \
    vc-graphics-hardfp \
    linux-firmware \
    bcm2835-tests \
    bash \
    nano \
    zram \
    rsync \
    screen \
    gkrellm \
    cpufrequtils \
    python-shell \
    packagegroup-base-alsa \
    alsa-utils-speakertest \
    alsa-utils-amixer \
    mpd \
    mpc \
    empcd \
    mc \
    vim \
    file \
    links \
    irssi \
    tzdata \
    ntp \
    ntpdate \
    ntp-utils \
    perl-module-socket \
    perl-module-getopt-std \
    usbutils \
    sysfsutils \
    lirc \
    lirc-exec \
    lirc-remotes \
    lirc-x \
    bluez-hcidump \
    bluez4-agent \
    wpa-supplicant \
"

# core-image bbclass provides core-boot and base packages
inherit core-image

XSERVER = " \
    packagegroup-core-x11 \
    packagegroup-core-x11-base \
    libxi \
    libxtst \
    libx11-locale \
    xorg-minimal-fonts \
    gdk-pixbuf-loader-ico \
    gdk-pixbuf-loader-bmp \
    gdk-pixbuf-loader-ani \
    gdk-pixbuf-xlib \
    liberation-fonts \
    xkbcomp \
    xmodmap \
    xrdb \
    setxkbmap \
"    

DESKTOP_APPS = " \
    imagemagick \
    feh \
    mupdf \
    xchat \
    vala-terminal \
    leafpad \
    leafpad-stock-icons \
    midori \
    pidgin \
    gnome-bluetooth \
    pcmanfm \
    xfmpc \
    gkrellm-client \
    gst-meta-video \
    gst-meta-audio \
    wifi-radar \
"

WINDOW_MANAGER = " \
    openbox \
    openbox-config \
    openbox-core \
    openbox-theme-bear2 \
    openbox-theme-clearlooks \
    openbox-theme-onyx \
    gtk-theme-clearlooks \
    gtk-theme-crux \
    gtk-theme-mist \
    clearlooks-theme-enable \
    gnome-icon-theme \
    gnome-icon-theme-enable \
    hicolor-icon-theme \
    obconf \
    tint2 \
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
