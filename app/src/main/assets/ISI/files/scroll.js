(function ($) {
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
        }
        return "";
    }
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires + "; path=/";
    }
    function validateEmail($email) {
        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        return emailReg.test( $email );
    }
    function scrollToSection(section) {
        if(section && section.length > 1){
            $("html, body").animate({
                scrollTop: $(section).offset().top
            }, 500);
            window.location.hash = section;
        }

    }

    function detectBreakpoint() {

        if (window.outerWidth < 768) {
            //select interested inputs and set name accordingly to avoid duplicate inputs across the DOM
            $(".desktopInput").attr("name", "");
            $(".mobileInput").each(function() {
                var dataname = $(this).data("name");
                $(this).attr("name", dataname);
            });
        } else {
            $(".mobileInput").attr("name", "");
            $(".desktopInput").each(function() {
                var dataname = $(this).data("name");
                $(this).attr("name", dataname);
            });
        }
        if (window.outerWidth <= 1280) {
            //$(".dropdown>a").dropdown();
            $(".dropdown>a").attr("data-toggle", "dropdown");
            $(".dropdown>a").attr("data-hover", false);
        } else {
            $(".dropdown>a").attr("data-toggle", false);
        }

    }


    function resizePlayer(proportion) {
        var videoskin = $(".videoSkin"),
            video = $(".innerstage").find(".BrightcoveExperience"),
            height = $(window).height(),
            fixedfooterheight = $(".navbar-fixed-bottom").outerHeight(true),
            menuheight = $("[role=navigation]").outerHeight(true),
            visibleheight = height - fixedfooterheight - menuheight;
        if (!videoskin) return;
        if (!proportion) {
            proportion = 1.77;
        }
        if ($(videoskin).outerHeight() > visibleheight) {
           video.height(visibleheight - 140);
           video.width(visibleheight)
        }
    }

    function show_discl(show){
        if(show){


            $("#collapseOne").addClass("in");
            if($('#headingOne').find('.arrow').hasClass("collapsed")){
                $('#headingOne').find('.arrow').removeClass("collapsed");
            }
            if ($("#collapseOne").hasClass("in")) {
                 $("#accordion").addClass("out");
            }
            $('body').addClass('footer-expanded');
        }else{
            if($("#collapseOne").hasClass("in")){
                $("#collapseOne").removeClass("in");
            }
            $('body').removeClass('footer-expanded');
        }
    }

    function calculate_points($test_type){
        var points = 0;
        $.each($test_type.find(".row"), function(rowindex, row) {
            var index = $(row).find(".active").index();
            var analytics_text = ["Sitting and reading_", "Watching TV_", "Sitting inactive_", "Passenger in car_", "Lying down_", "Sitting and talking_", "Sitting after lunch_", "Stopped in a car_"];
            if (index < 0) index = 0;
            points += index;
            if($("body").hasClass("patient")){
                ga('send', 'event', 'Patient_ESS', 'Q'+(rowindex + 1), analytics_text[rowindex]+index);
            }else{
                ga('send', 'event', 'HCP_ESS', 'Q'+(rowindex + 1), analytics_text[rowindex]+index);
            }

        });
        if($("body").hasClass("patient")) {
            ga('send', 'event', 'Patient_ESS', 'click', 'ESS Calculate');
            ga('send', 'event', 'Patient_ESS',  'ess_score', points);
        }else{
            ga('send', 'event', 'HCP_ESS', 'click', 'Calculate');
            ga('send', 'event', 'HCP_ESS', 'ess_score', points);

        }
        $test_type.find(".points").text(points);
    }

    function calculate_snspoints($test_type){
        var points = 20,
            weights = [6, 9, -5, -11, -13];
        $.each($test_type.find(".row"), function(rowindex, row) {
            var index = $(row).find(".active").data("index");
            var analytics_text = ["Unable to fall asleep_", "Feel bad not well rested_", "Take a nap during day_", "Weak knees buckling_", "Sagging of the jaw_"];
            if (typeof index === "undefined") index = 1;
            points += ( weights[rowindex] * index);
            if($("body").hasClass("patient")){
                ga('send', 'event', 'Patient_SNS', 'Q'+(rowindex + 1), analytics_text[rowindex]+index);
            }

        });
        if($("body").hasClass("patient")) {
            ga('send', 'event', 'Patient_SNS', 'click', 'SNS Calculate');
            ga('send', 'event', 'Patient_SNS', 'sns_score', points);
        }
        $test_type.find(".points").text(points);
    }

	$( document ).ready(function() {
        var currentUrl = window.location.href;

        // Checks if a mobile Browser is used
        window.mobilecheck = function() {
            var check = false;
            (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od|ad)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
            return check;
        };

        RegExp.escape = function(text) {
            return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
        }

        //implement  50% size on superscripts contaning registered trademarks or registered trademarks
        $('sup:contains("®")').css("top", "-1em").css("font-size", "50%");
        $('sup:contains("™")').css("font-size", "50%");

        function highlight(term, title, base) {
            if (!term) return;
            base = base || document.body;
            var t = RegExp.escape(term);
            var re = new RegExp("(?:(^|[^a-z]))(([^a-z]*)(?=" + t + ")" + t + ")(?![a-z])", "gi"); //... just use term
            var replacement = "<span data-toggle='tooltip' title='" + title + "'>$1$2</span>";
            $("*", base).contents().each( function(i, el) {
                if (el.nodeType === 3) {
                    if (el.parentElement && el.parentElement.tagName === "A") {
                        return;
                    }
                    var data = el.data;
                    if (data = data.replace(re, replacement)) {
                        var wrapper = $("<span>").html(data);
                        $(el).before(wrapper.contents()).remove();
                    }
                }
            });
        }

        highlight('cataplexy', 'A sudden, brief loss of muscle strength or control caused by a strong emotion such as laughing, joking, or anger.', $("#maincont"));
        highlight('ess', 'A scale for measuring and self reporting a general level of daytime sleepiness.', $("#maincont"));
        highlight('eds', 'The inability to stay awake and alert during the day, resulting in unintended lapses into drowsiness or sleep.', $("#maincont"));

        $('[data-toggle="tooltip"]').tooltip({
            placement: function (context, source) {
                var offset = $(source).offset();
                var position = $(source).position();
                if (position.left < 100) {
                    return "right";
                }
                if (position.right < 100) {
                    return "left";
                }
                if ((offset.top - window.scrollY) < 110){
                    return "bottom";
                }

                return "top";
            }
        });

        //initialise tooltips
        if(window.mobilecheck()){
            //scroll fix for fixed footer
            var scrollable = document.getElementById("accordion");
            new ScrollFix(scrollable);
            var tipOpen = false;
            //fix to close tooltip on mobile by click in content
            $("#maincont").on("click touchstart", function(e){
                var tooltip = $("#maincont").find(".tooltip");

                if(tooltip.length > 1){
                    tooltip.each(function(index){
                        if(!$(tooltip[index]).hasClass("in")){
                            $(tooltip[index]).hide()
                        }
                    });
                }else{
                    if(tooltip && tooltip.hasClass("in") && tipOpen){
                        $(".tooltip").hide();
                        tipOpen = false;
                    }else if(tooltip && tooltip.hasClass("in")){
                        tipOpen = true;
                    }else{
                        tipOpen = false;
                    }
                }
            });
        }


        detectBreakpoint();
        //resizePlayer();
        if (window.location.hash) {
            setTimeout(function() {
                scrollToSection(window.location.hash);
            }, 300);
            $(".mainnav").find(".active_section").removeClass("active_section");
            $('a[href$="'+window.location.hash+'"]').addClass("active_section");
        }else if(window.location.pathname == "/contact-us.html" || window.location.pathname == "/privacy-policy.html" || window.location.pathname == "/sitemap/" || window.location.pathname == "/providers/office-support/" || window.location.pathname == "/providers/contact.html"){
            $(".mainnav").find(".active_section").removeClass("active_section");
            $(".downnav").find('a[href$="'+window.location.pathname.substr(1)+'"]').addClass("active_section");
        }else{
            var pathName = window.location.pathname.slice(1, -1).split('.')[0];

            $(".mainnav").find(".active_section").removeClass("active_section");
            $(".dropdown.active li").each(function (i, el) {
                var $el = $(el);

                if ($el.find('a').attr('href').split('.')[0] === pathName) {
                    $el.addClass('active_section');
                }
            });
        }
        //var dump = getCookie("collapse");
        if($("body").hasClass("patient")){
            if(getCookie("collapse") !== "true"){
                if(getCookie("collapse") !== "false"){
                    document.cookie='collapse=false; path=/';
                }
                show_discl(true);
            }
        }else if($("body").hasClass("providers")){
            if(getCookie("collapse-hcp") !== "true"){
                if(getCookie("collapse-hcp") !== "false"){
                    document.cookie='collapse-hcp=false; path=/';
                }
                show_discl(true);
            }
        }


        $(document).on("click", "a", function(e){
                if (e.which == 1 || e.which == 2) {
                    $(e.target).blur();
                }
        });

        $("a[href^='tel:']").on("click", function(e){
            //If not mobile Browser, prevent Link action
            if(!window.mobilecheck()){
                e.preventDefault();
                return false;
            }
        });

        $( window ).on( "orientationchange", function( event ) {
            $("#header_collapse").find(".open").each(function(i){
               $(this).removeClass("open");
            });

            if($("#header_collapse").hasClass("in")){
                $("#header_collapse").removeClass("in");
            }
        });

        if(!window.mobilecheck()){
            $(".dropdown").on("mouseenter", function(e){
                $(this).addClass("open");
            });
            $(".dropdown").on("mouseleave", function(e){
                $(this).removeClass("open");
            });
        }

        $('#accordion').on('shown.bs.collapse', function() {
            $('body').addClass('footer-expanded');
        }).on('hidden.bs.collapse', function() {
            $('body').removeClass('footer-expanded');
        });

        $("a[data-toggle=collapse]").on("click", function(e){
            if($(e.target).data("parent") == "#accordion") {
                //var dump = getCookie("collapse");
                if($("body").hasClass("patient")){
                    if(getCookie("collapse") !== "true"){
                        document.cookie = "collapse=false; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
                        document.cookie = "collapse=true; path=/";
                    }
                }else if($("body").hasClass("providers")) {
                    if (getCookie("collapse-hcp") !== "true") {
                        document.cookie = "collapse-hcp=false; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
                        document.cookie = "collapse-hcp=true; path=/";
                    }
                }

                if (window.outerWidth <= 1005) {
                    if ($("#collapseOne").hasClass("in")) {
                        $("#accordion").removeClass("out");
                        $("#collapseOne").css("display","none");
                        $("#accordion").css("height", "");
                        $("#headingOne").find(".hidden-on-mobile-collapsed").each(function (index) {
                            if ($(this).hasClass("visible")) {
                                $(this).removeClass("visible");
                            }
                        });
                    } else {
                        $("#accordion").addClass("out");
                        $("#collapseOne").css("display","block");
                        var height = $("#headingOne").outerHeight() + $("#collapseOne .container").outerHeight() + 30;
                        $("#accordion.out").css("height", height);
                        $("#headingOne").find(".hidden-on-mobile-collapsed").each(function (index) {
                            $(this).addClass("visible");
                        });
                    }
                }
            }
            if($(e.target).data("parent") == "#faq") {
                var offset = 0;
                var idopen = $(".panel-collapse.in").attr("id");
                var idnew = $(e.target).attr("href");
                idopen = "#"+idopen;
                if(idnew > idopen){
                    if(window.mobilecheck()){
                        offset = $(e.target).offset().top - $(".panel-collapse.in").outerHeight() - 50;
                    }else{
                        offset = $(e.target).offset().top - $(".panel-collapse.in").outerHeight() - $("#maincont").offset().top;
                    }
                    offset = $(e.target).offset().top - $(".panel-collapse.in").outerHeight() - $("#maincont").offset().top;
                }else{
                    offset = $(e.target).offset().top - $("#maincont").offset().top;
                }
                $("html, body").animate({
                    scrollTop: offset
                }, 500);
            }
        });

        $('.active .dropdown-menu a').on("click", function(e) {
            var section;

            if (document.URL.indexOf('#') !== -1) {
                section = $.attr(this, "href").split("#")[1];
            }

            if(section && section !== "office-support" && section !== "contact-us" && $(".contact-us").length <= 0) {
                e.preventDefault();
                scrollToSection("#" + section);
                $(".mainnav").find(".active_section").removeClass("active_section");
                $(e.target).addClass("active_section");
            }else{
                if(section == "contact-us"){
                    e.preventDefault();
                    var path = $.attr(this, "href").split("#")[0];
                    window.location.hash = "";
                    window.location.pathname = path+"contact-us.html";
                }else if($(document).find("section.contact-us")){
                    //window.location.href = window.location.hostname + "/" + $.attr(this, "nameProp");
                }else{
                    e.preventDefault();
                    var path = $.attr(this, "href").split("#")[0];
                    window.location.pathname = path;
                }
                $(".mainnav").find(".active_section").removeClass("active_section");
                $(e.target).addClass("active_section");
            }
        });

        $("#back-to-top-btn").on("click", function(e){
            e.preventDefault();
            $("html, body").animate({
                scrollTop: 0
            }, 500);
        });

        $("a[data-target=#search-desktop]").on("click", function(e){
            e.preventDefault();
            if(!$("#search-desktop").hasClass("isOpen")){
                $("#search-desktop").find("ul.dropdown-search").css("display","block");
                $("#search-desktop").addClass("isOpen");
            }else{
                $("#search-desktop").find("ul.dropdown-search").css("display","none");
                $("#search-desktop").removeClass("isOpen");
            }
        });

        //Contact Form Placeholder Fix for <= IE9

        $('[placeholder]').each(function(index) // scan for every element containing a placeholder attribute
        {   // equip every object's value width placeholder text
            if(!window.mobilecheck()) {
                if ($(this).val() == '') $(this).val($(this).attr('placeholder'));
                // on focusing the element
                $(this).focus(function () {
                    if ($(this).val() == $(this).attr('placeholder')) $(this).val('');
                });
                // on bluring the element
                $(this).blur(function () {
                    if ($(this).val() == '') $(this).val($(this).attr('placeholder'));
                });
                // don't submit/send placeholder text
                $(this).parents("form").submit(function () {
                    $(this).find('[placeholder]').each(function () {
                        if ($(this).val() == $(this).attr('placeholder')) $(this).val('');
                    });
                });
            }
        });

        $("#submit-contact").on("click", function(e){
            var name = $("#name").val();
            var email = $("#email").val();
            var phone = $("#phone").val();
            var missing = 0;
            if( $("#name").val() == "" || $("#name").val() == "Enter Name" ){
                $("#name").parent("div").addClass("isRequired");
                missing = 1;
            }else{
                $("#name").parent("div").removeClass("isRequired");
            }
            if( $("#email").val() == "" || !validateEmail($("#email").val()) ){
                $("#email").parent("div").addClass("isRequired");
                missing = 1;
            }else{
                $("#email").parent("div").removeClass("isRequired");
            }
            if($("#phone").val() == "" || $("#phone").val() == "Phone"){
                $("#phone").parent("div").addClass("isRequired");
                missing = 1;
            }else{
                $("#phone").parent("div").removeClass("isRequired");
            }
            if(missing == 1){
                e.preventDefault();
            }
        });

        $("#topnav-safety-link, #bottom-safety-link, #safety-link").on("click", function(e){
            e.preventDefault();
            scrollToSection("#safety-information");
        });

        $("a[rel=footnote]").on("click", function(e){
            var header = $('.navbar.navbar-fixed-top.mainnav.providers-main-nav .container').height();

            e.preventDefault();
            $('html,body').animate({scrollTop: $($(this).attr('href')).offset().top - (header - 40)}, 500);
        });

        window.onresize = detectBreakpoint;

            var $ess = $("#ess_test"),
                $sns = $("#sns_test");
        $ess.on("click", "span", function(e) {
            $(this).siblings().removeClass("active");
            $(this).addClass("active");
        });

        $sns.on("click", "span", function(e) {
            $(this).parent().siblings().find("span").removeClass("active");
            $(this).addClass("active");
        });

        /* EDS */
        $("#calc-test-result-btn").on("click", function(e){
            e.preventDefault();
            calculate_points($ess);
        });

        /* SNS */
        $("#calc-sns-result-btn").on("click", function(e){
            e.preventDefault();
            calculate_snspoints($sns);
        });



    });
})(jQuery);