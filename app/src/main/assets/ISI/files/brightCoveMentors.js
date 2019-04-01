var BCL = {};

(function () {
    var $videoStill = $(".videoStill"),
        $placeHolderVideo = $('.innerstage .underbelly, .innerstage .playButton, .playlistMobile .playlist-element'),
        lastVideoId = null;

    function loadVideo (videoRef) {
        lastVideoId = videoRef;
        if (BCL.videoPlayer.loadVideo) {
            BCL.videoPlayer.loadVideo(videoRef);
        } else {
            BCL.videoPlayer.loadVideoByID(videoRef);
        }
    }

    function addPlayer (videoId) {
        // if we don't already have a player
        var brightCoveInstance = $("#innerstage").find(".BrightcoveExperience"),
            underbelly = $("#innerstage").find(".underbelly");

        if (BCL.isPlayerAdded == false) {
            BCL.isPlayerAdded = true;
            var playerHTML = "";
            // set the videoID to the selected video
            if (videoId) {
                BCL.playerData.videoID = videoId;
                lastVideoId = videoId;
            }
            // populate the player object template
            playerHTML = BCL.markup(BCL.playerTemplate, BCL.playerData);
            // inject the player code into the DOM
            $("#innerstage").append(playerHTML);
            // instantiate the player
            setTimeout(function () {
                brightcove.createExperiences();
            }, 0);
            underbelly.css("z-index", -1);
            $(".innerstage").find(".playButton").css("z-index", -1);
            brightCoveInstance.css("z-index", 1);
        } else {
            underbelly.css("z-index", -1);
            $(".innerstage").find(".playButton").css("z-index", -1);
            brightCoveInstance.css("z-index", 1);
            loadVideo(videoId);
        }
    }

    function addPlayerMobile ($el, videoId) {
        var w = $el.width(),
            h = Math.round((w * 9) / 16);

        if (videoId) {
            $('#myExperienceMobile').remove();
            $('.hiddenVideoPlayer')
                .show()
                .removeClass('hiddenVideoPlayer');

            BCL.playerData.videoID = videoId;
            $el.prepend(BCL.markup(getPlayerTemplateMobile(w, h), BCL.playerData));
            $el.find('.playButton, .videopreview')
                .addClass('hiddenVideoPlayer')
                .hide();

            brightcove.createExperiences();
        }
    }

    function updatePreview (videoEl) {
        var name = videoEl.data("mentorname") || "",
            caption = videoEl.data("mentorquote") || "",
            videoId = videoEl.data("videoid"),
            sanitizedName = name.replace(" ", ""),
            previewUrl = "/assets/images/mentors/" + sanitizedName + ".png",
            brightCoveInstance = $("#innerstage").find(".BrightcoveExperience"),
            $underbelly = $(".underbelly"),
            $videoStill = $(".videoStill");

        $underbelly.find(".mentorName").text(name);
        $underbelly.find(".mentorQuote").text(caption);
        $videoStill.attr("src", previewUrl);
        $videoStill.data("videoref", videoId);

        if (BCL.isPlayerAdded) {
            BCL.videoPlayer.pause();
            brightCoveInstance.css("z-index", -1);
            $(".underbelly").css("z-index", 10);
            $(".innerstage").find(".playButton").css("z-index", 10);
        }
    }

    function loadPlaylist (tab) {
        var playlistRef = tab.attr("id"),
            chosen = $(".playlist[data-playlistRef='" + playlistRef + "']"),
            firstVideo = chosen.find(".videopreview:first");
        BCL.isMobile = $(".tabs").css("display") === "none";

        $(".tab.active").removeClass("active");
        tab.addClass("active");

        $(".playlist").removeClass("active");
        chosen.addClass("active");
        if (BCL.isPlayerAdded) {
            if (BCL.videoPlayer.stop) {
                BCL.videoPlayer.stop();
            } else {
                BCL.videoPlayer.pause();
            }
        }
        BCL.hasCarouselAlready = chosen.find(".playlistUl").hasClass("slick-initialized");
        if (!BCL.hasCarouselAlready) {
            setTimeout(function () {
                chosen.find(".playlistUl").slick({
                    slidesToShow: 4,
                    slidesToScroll: 1,
                    infinite: false,
                    arrows: true,
                    responsive: [
                        {
                            breakpoint: 992,
                            settings: {
                                slidesToShow: 3,
                                slidesToScroll: 3
                            }
                        }
                    ]
                });
            }, 0);
        }
        firstVideo.addClass("active");
        if (!isMobileDevice()) {
            firstVideo.click();
        }
    }

    function onTemplateLoaded (id) {
        // get a reference to the player
        BCL.player = brightcove.getExperience(id);

        if (BCL.player) {
            // get references to the experience and video player modules
            BCL.experienceModule = BCL.player.getModule(APIModules.EXPERIENCE);
            BCL.videoPlayer = BCL.player.getModule(APIModules.VIDEO_PLAYER);
        } else {
            BCL.player = brightcove.api.getExperience(id);
            // get references to the experience and video player modules
            BCL.experienceModule = BCL.player.getModule(APIModules.EXPERIENCE);
            BCL.videoPlayer = BCL.player.getModule(APIModules.VIDEO_PLAYER);
        }
    }

    function onMobileTemplateLoaded (id) {
        var $el = $('#' + id),
            w = $el.width(),
            h = (w * 9) / 16;

        onTemplateLoaded('myExperienceMobile');

        if ($el.is('object')) {
            $el.css('height', h);
        }
    }

    function onTemplateReady () {

        function handleVideoExpand () {
            var isMobile = $(".tabs").css("display") === "none";
            if (!isMobile) {
                return;
            }
            $(".innerstage").find(".BrightcoveExperience")
                .css("position", "fixed").css("z-index", 1031).removeClass('transparent');
        }

        function handleVideoClose () {
            var isMobile = $(".tabs").css("display") === "none";
            if (!isMobile) {
                return;
            }
            $(".innerstage").find(".BrightcoveExperience")
                .css("position", "absolute").css("z-index", 1).addClass('transparent');
        }

        var videoRef = $(".videoStill").data("videoref");
        loadVideo(videoRef);
        BCL.videoPlayer.addEventListener(brightcove.api.events.MediaEvent.PLAY, handleVideoExpand);
        BCL.videoPlayer.addEventListener(brightcove.api.events.MediaEvent.STOP, handleVideoClose);
    }

    /*
     simple HTML templating function
     array example:
     demo.markup("<div>{{1}}, {{0}}</div>", ["John", "Doe"]);
     object example:
     demo.markup("<div>{{last}}, {{first}}</div>", {first:"John", last:"Doe"});
     */
    function markup (html, data) {
        var m,
            i = 0,
            match = html.match(data instanceof Array ? /{{\d+}}/g : /{{\w+}}/g) || [];

        while (m = match[i++]) {
            html = html.replace(m, data[m.substr(2, m.length - 4)]);
        }
        return html;
    }

    function getPlayerTemplateMobile (w, h) {
        return '<div style="display:none"></div>' +
            '<object id="myExperienceMobile" class="BrightcoveExperience">' +
            '<param name="includeAPI" value="true" />' +
            '<param name="bgcolor" value="#ffffff" />' +
            '<param name="width" value="' + (w ? w : '{{width}}') + '" />' +
            '<param name="height" value="' + (h ? h : '{{height}}') + '" />' +
            '<param name="playerID" value="{{playerID}}" />' +
            '<param name="playerKey" value="{{playerKey}}" />' +
            '<param name="isVid" value="true" />' +
            '<param name="isUI" value="true" />' +
            '<param name="dynamicStreaming" value="true" />' +
            '<param name="@videoPlayer" value="{{videoID}}" />' +
            '<param name="templateLoadHandler" value="BCL.onMobileTemplateLoaded">' +
            '<param name="secureConnections" value="true" />' +
            '<param name="secureHTMLConnections" value="true" />'+
            '</object>';
    }

    // data for our player -- note that it must have ActionScript/JavaScript APIs enabled!!
    BCL.playerData = {
        "playerID": "2360098316001",
        "playerKey": "AQ~~,AAABC2NhJXk~,whGlo0Pc2IvqFLcBQMTMaUbnxnwuAg8_s",
        "videoID": "2360098316001",
        "height": "100%",
        "width": "100%"
    };
// flag to keep track of whether there is a player
    BCL.isPlayerAdded = false;
// template for the player object - will populate it with data using markup()
    BCL.playerTemplate = '<div style="display:none"></div>' +
        '<object id="myExperience" class="BrightcoveExperience">' +
        '<param name="includeAPI" value="true" />' +
        '<param name="bgcolor" value="#ffffff" />' +
        '<param name="width" value="{{width}}" />' +
        '<param name="height" value="{{height}}" />' +
        '<param name="wmode" value="opaque" />' +
        '<param name="playerID" value="{{playerID}}" />' +
        '<param name="playerKey" value="{{playerKey}}" />' +
        '<param name="isVid" value="true" />' +
        '<param name="isUI" value="true" />' +
        '<param name="dynamicStreaming" value="true" />' +
        '<param name="@videoPlayer" value="{{videoID}}" />' +
        '<param name="templateLoadHandler" value="BCL.onTemplateLoaded">' +
        '<param name="templateReadyHandler" value="BCL.onTemplateReady">' +
        '<param name="secureConnections" value="true" />' +
        '<param name="secureHTMLConnections" value="true" />'+
        '</object>';
    BCL.addPlayer = addPlayer;
    BCL.addPlayerMobile = addPlayerMobile;
    BCL.updatePreview = updatePreview;
    BCL.loadPlaylist = loadPlaylist;
    BCL.onTemplateLoaded = onTemplateLoaded;
    BCL.onMobileTemplateLoaded = onMobileTemplateLoaded;
    BCL.onTemplateReady = onTemplateReady;
    BCL.markup = markup;

    function onPlaceHolderClick (ev) {
        var $el;

        ev.preventDefault();
        ev.stopPropagation();

        if (isMobileDevice()) {
            $el = $(ev.target).closest('[data-videoid]');
            BCL.addPlayerMobile($el, $el.data('videoid'));
        } else {
            $('.innerstage #myExperience').show();
            BCL.addPlayer($videoStill.data("videoref"));
        }
    }

    $placeHolderVideo.on('click', onPlaceHolderClick);
    $videoStill.on("click", onPlaceHolderClick);

    $('.playlistContainer, .playlistMobile').on('click', '.videopreview', function (e) {
        var target = $(e.currentTarget);
        e.preventDefault();
        e.stopPropagation();

        $('.videopreview.active').removeClass("active");
        target.addClass("active");
        $('.innerstage #myExperience').hide();
        $placeHolderVideo.css('z-index', 10);

        BCL.updatePreview(target.parent());
    });

    $(".tab").on("click", function (e) {
        var target = $(e.currentTarget);
        BCL.loadPlaylist(target);
    });

    BCL.loadPlaylist($(".tab:first"), true);

    function isIe9 () {
        return (/*@cc_on !@*/false && document.documentMode <= 9);
    }

    function isMobileDevice () {
        return $('.playlistMobile').is(':visible');
    }

    function init () {
        if (isMobileDevice()) {
            $('.playlistMobile .playlist-element').each(function (i, el) {
                el.id = 'face' + (i + 1);
            });

            $('.container').addClass('isMobile');
        }
    }

    init();
}());