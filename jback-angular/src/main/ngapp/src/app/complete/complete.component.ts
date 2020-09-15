import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-complete',
  templateUrl: './complete.component.html',
  styleUrls: ['./complete.component.scss']
})
export class CompleteComponent implements OnInit {

  private id: string;

  code: string;

  constructor(
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
  }
}
