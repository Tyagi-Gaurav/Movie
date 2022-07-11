package main

import (
	"io/ioutil"
	"testing"

	"github.com/ohler55/ojg/jp"
	"github.com/ohler55/ojg/oj"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestConfigPublish(t *testing.T) {
	apps := []string{"contentUpload", "user"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.ConfigPublishUrl())
		util.PanicOnError(err)
		util.ExpectStatus(t, resp, 200)

		bodyBytes, err := ioutil.ReadAll(resp.Body)
		util.PanicOnError(err)

		obj, err := oj.ParseString(string(bodyBytes))
		util.PanicOnError(err)

		x, err := jp.ParseString(`$..password`)
		util.PanicOnError(err)

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
