<?php

namespace App\Http\Controllers\Api\V1;

use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Auth\Events\Registered;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function show($user)
    {
        //
        $user = User::find($user);

        $user = $user ? array($user) : [];

        $user = [
            "total_results" => count($user),
            "results" => $user,
        ];

        return response()->json($user);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, User $user)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function destroy(User $user)
    {
        //
    }

    public function memberGymLocation($member) {
        $member =  User::find($member);
        
        $gym = $member ? array($member->memberGymLocation) : [];

        $gym = [
            "total_results" => count($gym),
            "results" => $gym,
        ];
        
        return response()->json($gym);
    }

    public function addMemberGymLocation(Request $request, $member) {
    
        $validator = Validator::make($request->all(), [
            'location_id' => 'required|exists:gym_locations_92879,id',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $member =  User::find($member);

        if ($member != null) {
            $member->gym_location_id = $request->location_id;

            $success = $member->save();
    
        } else {
            $success = false;
    
        }
        
        if ($success) {
            $response = [
                "status" => "success",
                "message" => "Gym Location added successfully",
            ];
        } else {
            $response = [
                "status" => "Failed",
                "message" => "Gym Location not added",
            ];
        }
        
        return response()->json($response);
    }

    public function memberWorkouts($member) {
        $member =  User::find($member);        
        
        $workouts = $member ? $member->workouts : [];

        $response = [
            'status' => 'success',
            'total_results' => count($workouts),
            'results' => $workouts,
        ];
       
        return response() ->json($response);
    }

     /**
     * Handle a login request to the application.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Http\Response|\Illuminate\Http\JsonResponse
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function login(Request $request)
    {
        $validator = $this->validateLogin($request);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }
        
        if ($this->authenticate($request)){
            $response = [
                'status' => 'success',
                'message' => 'successful authentication',
                'user' => Auth::user(),
            ];
        } else {
            $response = [
                'status' => 'fail',
                'message' => 'Invalid credentials',
            ];
        }
        
        return response()->json($response);
    }

    /**
     * Validate the user login request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return void
     */
    protected function validateLogin(Request $request)
    {
        return Validator::make($request->all(), [
            $this->username() => 'required|string|email',
            'password' => 'required|string',
        ]);
    }

    /**
     * Get the login username to be used by the controller.
     *
     * @return string
     */
    public function username()
    {
        return 'email';
    }

    public function authenticate(Request $request)
    {
        $credentials = $request->only('email', 'password');

        if (Auth::attempt($credentials)) {
            // Authentication passed...
            return true;
        }
    }

    /**
     * Log the user out of the application.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function logout(Request $request)
    {        
        Auth::logout();

        $response = [
            'status' => 'success',
            'message' => 'successful Logging out',
            'user' => Auth::user(),
        ];

        return response() ->json($response);
    }

    /**
     * Handle a registration request for the application.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function register(Request $request)
    {
        $validator = $this->validator($request->all());
        // ->validate()
        // return 'true';
        if ($validator->fails()) {
            return response()->json($validator->errors());
        }
        
        event(new Registered($user = $this->create($request->all())));        
        
        if ($user){
            $response = [
                'status' => 'success',
                'message' => 'successful registration',
                'user' => $user,
            ];
        } else {
            $response = [
                'status' => 'fail',
                'message' => 'An error occured, please try again',
            ];
        }
        
        return response()->json($response);
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'firstname' => 'required|string|max:255',
            'lastname' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:users_92879',
            'password' => 'required|string|min:6|confirmed',
        ]);
    }

     /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\User
     */
    protected function create(array $data)
    {
        return User::create([
            'firstname' => $data['firstname'],
            'lastname' => $data['lastname'],
            'email' => $data['email'],
            'password' => Hash::make($data['password']),
        ]);
    }
}
