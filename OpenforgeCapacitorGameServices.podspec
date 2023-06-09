
  Pod::Spec.new do |s|
    s.name = 'OpenforgeCapacitorGameServices'
    s.version = '1.0.6'
    s.summary = 'A native only plugin for googles play services library and apples game center library'
    s.license = 'MIT'
    s.homepage = 'https://github.com/openforge/capacitor-game-connect'
    s.author = 'OpenForge'
    s.source = { :git => 'https://github.com/openforge/capacitor-game-connect', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
    s.static_framework = true
  end