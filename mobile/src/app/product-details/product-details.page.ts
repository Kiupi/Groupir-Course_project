import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import * as $ from "jquery";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.page.html',
  styleUrls: ['./product-details.page.scss'],
})
export class ProductDetailsPage implements OnInit {
  productId: Number;
  product: any;
  selectedOptionId: Number;

  constructor(private route: ActivatedRoute) {
    let page = this;
    this.selectedOptionId = 0;
    this.route.queryParams.subscribe(params => {
      page.productId = params["id"];
      page.product = {
        id: page.productId,
        name: "Sun Shield",
        description: "vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum",
        image: "https://ae01.alicdn.com/kf/HTB1YxCwSVXXXXXWXXXXq6xXFXXXQ/Car-Windshield-Sunshades-Window-Sun-Shield-Visor-Silver-Car-Shade-Sun-Protection-Size-92-cm-142.jpg_640x640.jpg",
        options: [{
          id: 0,
          name: "92 cm"
        }, {
          id: 1,
          name: "104 cm"
        }, {
          id: 2,
          name: "116 cm"
        }],
        bought: 3,
        maxBought: 20
      }
    });
  }

  ngOnInit() {
    let ProgressBar = {
      defaults: {padding: 1, innerPadding: 4, progress: 0},
      init: function(el, options) {
        this.options = $.extend({}, ProgressBar.defaults, options);
        this.$el = $(el);
        this.$svg = this.$el.find('svg');
        this.$svg.height(50);
        this.$svg.css({"width": "100%"});
        this.$container = this.$svg.find('#container');
        this.$bar = this.$svg.find('#bar');
        this.options.supportSVGFilter = this.supportSVGFilter();
        if(!this.options.supportSVGFilter) {
          this.$container.attr('filter', null);
          this.$container.attr('stroke-width', '1');
        }
        this.draw();
      },
      set: function(progress) {
        this.options.progress = progress;
        this.draw();
      },
      draw: function() {
        let containerOptions = {
          bar: this.$container,
          paddingX: this.options.padding,
          paddingY: this.options.padding,
        };
        let barOptions = {
          bar: this.$bar,
          paddingX: this.options.padding,
          paddingY: this.options.padding + this.options.innerPadding,
          width: this.options.progress
        };
        this.drawBar(containerOptions);
        this.drawBar(barOptions);
      },
      drawBar: function(options) {
        let width = this.$svg.width();
        let height = this.$svg.height();
        let template = "M{LTP} {RTP} A1,1 0 1 1 {RBP} L{LBP} A1,1 0 1 1 {LTP} z";
        let topY = options.paddingY;
        let bottomY = height - options.paddingY;
        let leftX = options.paddingX + height/2;
        let barWidth = typeof options.width == "undefined" ? width : width * options.width;
        barWidth = Math.max(barWidth, height + options.paddingX*2);
        let rightX = barWidth - options.paddingX - height/2;
        let result = template.
        replace(/{LTP}/g, leftX + "," + topY).
        replace(/{RTP}/g, rightX + "," + topY).
        replace(/{LBP}/g, leftX + "," + bottomY).
        replace(/{RBP}/g, rightX + "," + bottomY);
        options.bar.attr('d', result);
      },
      supportSVGFilter: function() {
        return typeof SVGFEColorMatrixElement !== "undefined" && SVGFEColorMatrixElement.SVG_FECOLORMATRIX_TYPE_SATURATE==2;
      }
    };
    $.fn.progressBar = function(options) {
      let originalArguments = arguments;
      this.each(function() {
        let progressBar = $.data(this, 'ProgressBar');
        if(!progressBar) {
          progressBar = Object.create(ProgressBar);
          progressBar.init(this, options);
          $.data(this, 'ProgressBar', progressBar);
        } else if(progressBar[options]) {
          progressBar[options](originalArguments[1]);
        }
      });
    };
    $.cssHooks.progressBar = {
      get: function(el) {
        let progressBar = $.data(el, 'ProgressBar');
        if(!progressBar) return 0;
        return progressBar.options.progress;
      },
      set: function(el, val) {
        val = parseFloat(val);
        let progressBar = $.data(el, 'ProgressBar');
        if(!progressBar) return;
        progressBar.set(val);
      }
    };
    $(window).resize(function() {
      $('.progress').progressBar('draw');
    });
    $('.progress').progressBar({padding: 0, innerPadding: 5, progress: 0});
    this.updateBar();
  }

  updateBar() {
    let progress = Math.min(1, this.product.bought / this.product.maxBought);
    $('.progress').stop();
    $('.progress').animate({progressBar: progress}, 300);
  }

  buyProduct() {
    this.product.bought++;
    this.updateBar();
  }
}
