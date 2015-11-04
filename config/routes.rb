ActionController::Routing::Routes.draw do |map|
  # The priority is based upon order of creation: first created -> highest priority.

  map.root :controller => 'landing', :action => 'landing'

  map.login  '/login',  :controller => 'core_user_management', :action => 'login'

  map.login  '/new_user/:id',  :controller => 'core_user_management', :action => 'new_user'

  map.login  '/edit_user_status/:id',  :controller => 'core_user_management', :action => 'edit_user_status'

  map.login  '/create_user',  :controller => 'core_user_management', :action => 'create_user'

  map.user_list  '/user_list',  :controller => 'core_user_management', :action => 'user_list'

  map.select_user_task  '/select_user_task',  :controller => 'core_user_management', :action => 'select_user_task'

  map.update_user_status  '/update_user_status',  :controller => 'core_user_management', :action => 'update_user_status'

  map.edit_roles  '/edit_roles',  :controller => 'core_user_management', :action => 'edit_roles'

  map.add_user_roles  '/add_user_roles',  :controller => 'core_user_management', :action => 'add_user_roles'

  map.void_role  '/void_role',  :controller => 'core_user_management', :action => 'void_role'

  map.edit_user  '/edit_user',  :controller => 'core_user_management', :action => 'edit_user'

  map.update_user  '/update_user',  :controller => 'core_user_management', :action => 'update_user'

  map.edit_password  '/edit_password',  :controller => 'core_user_management', :action => 'edit_password'

  map.update_password  '/update_password',  :controller => 'core_user_management', :action => 'update_password'

  map.authenticate  '/authenticate',  :controller => 'core_user_management', :action => 'authenticate'

  map.logout  '/logout/:id',  :controller => 'core_user_management', :action => 'logout'

  map.verify  '/verify/:id',  :controller => 'core_user_management', :action => 'verify'

  map.location  '/location',  :controller => 'core_user_management', :action => 'location'

  map.location_update  '/location_update',  :controller => 'core_user_management', :action => 'location_update'

  map.user_demographics  '/user_demographics',  :controller => 'core_user_management', :action => 'user_demographics'

  map.query '/query', :controller => 'settings', :action => 'query'

  map.log '/log', :controller => 'settings', :action => 'log'
  
	map.resources :national_identifiers, :collection => {
	  :request_gvh_ids => [:get, :post], 
	  :request_gvh_ids_ack => [:get, :post], 
	  :request_village_ids => [:get, :post],
	  :request_village_ids_ack => [:get, :post]
	}
	
  # Sample of regular route:
  #   map.connect 'products/:id', :controller => 'catalog', :action => 'view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   map.purchase 'products/:id/purchase', :controller => 'catalog', :action => 'purchase'
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   map.resources :products

  # Sample resource route with options:
  #   map.resources :products, :member => { :short => :get, :toggle => :post }, :collection => { :sold => :get }

  # Sample resource route with sub-resources:
  #   map.resources :products, :has_many => [ :comments, :sales ], :has_one => :seller
  
  # Sample resource route with more complex sub-resources
  #   map.resources :products do |products|
  #     products.resources :comments
  #     products.resources :sales, :collection => { :recent => :get }
  #   end

  # Sample resource route within a namespace:
  #   map.namespace :admin do |admin|
  #     # Directs /admin/products/* to Admin::ProductsController (app/controllers/admin/products_controller.rb)
  #     admin.resources :products
  #   end

  # You can have the root of your site routed with map.root -- just remember to delete public/index.html.
  # map.root :controller => "welcome"

  # See how all your routes lay out with "rake routes"

  # Install the default routes as the lowest priority.
  # Note: These default routes make all actions in every controller accessible via GET requests. You should
  # consider removing or commenting them out if you're using named routes and resources.
  map.connect ':controller/:action/:id'
  map.connect ':controller/:action/:id.:format'
end
