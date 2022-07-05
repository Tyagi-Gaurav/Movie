package main

import (
	"fmt"
	"io/ioutil"
	"testing"

	"github.com/ohler55/ojg/jp"
	"github.com/ohler55/ojg/oj"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/stretchr/testify/require"
)

func TestConfigPublish(t *testing.T) {
	apps := []string{"movie", "user"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.ConfigPublishUrl())

		if err != nil {
			panic(err)
		}

		require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
		bodyBytes, err := ioutil.ReadAll(resp.Body)

		if err != nil {
			panic(err)
		}

		obj, err := oj.ParseString(string(bodyBytes))

		if err != nil {
			panic(err)
		}

		x, err := jp.ParseString(`$..password`)
		if err != nil {
			panic(err)
		}

		ys := x.Get(obj)
		for _, v := range ys {
			switch val := v.(type) {
			case string:
				require.Equal(t, "******", val)
			case map[string]interface{}:
				if len(val) != 0 {
					require.Equal(t, "******", val["value"])
				}
			default:
				panic("Should have found atleast a single password node")
			}

		}
	}

}
