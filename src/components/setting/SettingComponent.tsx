import React, {useEffect, useState} from 'react';
import {View} from 'react-native';
import {StyleSheet} from 'react-native';
import ToggleSwitch from 'toggle-switch-react-native';
import {NativeModules} from 'react-native';

const {CalendarModule} = NativeModules;

// import {TouchableOpacity} from 'react-native-gesture-handler';

function Setting(): JSX.Element {
  const [isOn, setIsOn] = useState(false);
  //   const handleToggleService = (value: boolean) => {
  //     setIsOn(value);
  //   };
  useEffect(() => {
    CalendarModule.getValueFromJavaLayer(1, (data: boolean) => {
      console.log(data);
      setIsOn(data);
    });
  }, []);
  function handleToggleService(value: boolean) {
    //on
    if (value) {
      CalendarModule.TurnOnService();
    }
    //off
    else {
      CalendarModule.TurnOffService();
      //   CalendarModule.stopFeature();
    }
    setIsOn(value);
  }
  return (
    <View style={styles.container}>
      {/* <Text>Setting</Text> */}
      <View style={styles.toggleContainer}>
        <ToggleSwitch
          isOn={isOn}
          onColor="green"
          offColor="grey"
          label="Enabled feature"
          labelStyle={{color: 'black', fontWeight: '900'}}
          size="large"
          onToggle={value => handleToggleService(value)}
        />
      </View>
      {/* <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CalendarModule.startFeature();
        }}>
        <Text>Start service</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CalendarModule.TurnOffService();
        }}>
        <Text>Stop service</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CalendarModule.OpenPermission();
        }}>
        <Text>open permission</Text>
      </TouchableOpacity> */}
      {/* <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CalendarModule.startInterValNotification();
        }}>
        <Text>Start interval service</Text>
      </TouchableOpacity> */}
      {/* <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CalendarModule.stopFeature();
        }}>
        <Text>Stop service</Text>
      </TouchableOpacity> */}
    </View>
  );
}

export default Setting;
const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  button: {
    padding: 20,
    textAlign: 'center',
    // backgroundColor: 'blue',
    margin: 20,
  },
  toggleContainer: {
    padding: 10,
    marginLeft: 20,
  },
});
