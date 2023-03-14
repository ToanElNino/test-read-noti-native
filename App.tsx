/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import type {PropsWithChildren} from 'react';
import {NativeModules} from 'react-native';
import {PermissionsAndroid} from 'react-native';
import {check, PERMISSIONS, RESULTS} from 'react-native-permissions';

const {CalendarModule} = NativeModules;

import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import {createStackNavigator} from '@react-navigation/stack';
import {NavigationContainer} from '@react-navigation/native';

import SelectApp from './src/components/select-app/SelectAppComponent';
import Setting from './src/components/setting/SettingComponent';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

type RootStackParamList = {
  Setting: undefined;
  SelectApp: undefined;
  // Feed: {sort: 'latest' | 'top'} | undefined;
};
const RootStack = createStackNavigator<RootStackParamList>();

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };
  console.log(
    'check permission in react native',
    PermissionsAndroid.PERMISSIONS.ACCESS_NOTIFICATION_POLICY,
  );

  return (
    <NavigationContainer>
      <RootStack.Navigator
        initialRouteName="Setting"
        screenOptions={{
          headerShown: true,
        }}>
        <RootStack.Screen
          name="Setting"
          options={{
            title: 'Setting',
            headerStyle: {
              backgroundColor: '#0099ff',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: '500',
              marginLeft: 5,
            },
          }}
          component={Setting}
        />
        <RootStack.Screen
          name="SelectApp"
          component={SelectApp}
          // initialParams={{userId: user.id}}
        />
        {/* <RootStack.Screen name="Feed" component={Feed} /> */}
      </RootStack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  button: {
    padding: 20,
    textAlign: 'center',
    // backgroundColor: 'blue',
    margin: 20,
  },
});

export default App;
