import React, {useEffect, useState} from 'react';
import {View, Text, Dimensions} from 'react-native';
import {StyleSheet} from 'react-native';
import ToggleSwitch from 'toggle-switch-react-native';
import {NativeModules} from 'react-native';
import Separator from './Seperator';

const {CalendarModule} = NativeModules;
const {width} = Dimensions.get('screen');

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
      <Separator height={1} />
      <View style={styles.toggleContainer}>
        <Text style={styles.enableToggleText}>Enable Feature</Text>
        <ToggleSwitch
          isOn={isOn}
          onColor="green"
          offColor="grey"
          // label="Enabled feature"
          labelStyle={styles.toggleLabelStyle}
          size="medium"
          onToggle={value => handleToggleService(value)}
        />
      </View>
      <Separator height={1} />
      <View style={styles.enableToggleContainer}>
        <Text style={styles.enableToggleDetailText}>
          When you enable this feature, you will be able to send all messages in
          this device to other device.
        </Text>
      </View>
      <Separator height={1} />
    </View>
  );
}

export default Setting;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    paddingHorizontal: 25,
  },
  button: {
    // padding: 20,
    textAlign: 'center',
    // backgroundColor: 'blue',
    margin: 20,
  },
  toggleContainer: {
    // padding: 10,
    // marginLeft: 20,
    marginTop: 10,
    flexDirection: 'row',
    justifyContent: 'space-between',
    // width: width,
    paddingVertical: 5,
  },
  enableToggleText: {
    fontSize: 18,
    fontWeight: '500',
    color: 'black',
  },
  toggleLabelStyle: {
    color: 'black',
    fontWeight: '900',
  },
  enableToggleContainer: {
    marginVertical: 10,
    // backgroundColor: '#f5f5f5',
  },
  enableToggleDetailText: {
    fontSize: 15,
  },
});
