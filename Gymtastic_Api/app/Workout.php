<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Workout extends Model
{
    //
    use SoftDeletes;

    protected $table = 'workouts_92879';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id','workout_date', 'location_id', 'exercise_type', 'reps', 'sets',
    ];

    public function user() {
        return $this->belongsTo('App\User');
    }

    public function gymLocation() {
        return $this->belongsTo('App\GymLocation');
    }
}
