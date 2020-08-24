#import "FlSysShortcutPlugin.h"
#if __has_include(<fl_sys_shortcut/fl_sys_shortcut-Swift.h>)
#import <fl_sys_shortcut/fl_sys_shortcut-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "fl_sys_shortcut-Swift.h"
#endif

@implementation FlSysShortcutPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlSysShortcutPlugin registerWithRegistrar:registrar];
}
@end
