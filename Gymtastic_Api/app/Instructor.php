<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Instructor extends Model
{
    //

    protected $table = 'instructors_92879';

    public function gym() {
        return $this->belongsTo('App\GymLocation');
    }
}
